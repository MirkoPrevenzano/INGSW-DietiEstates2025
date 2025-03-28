import {  Component, OnInit, ViewChild } from '@angular/core';
import { DescriptionEstatesFormComponent } from '../description-estates-form/description-estates-form.component';
import { EstatesFeaturesFormComponent } from '../estates-features-form/estates-features-form.component';
import { SelectPlaceComponent } from '../select-place/select-place.component';
import { CommonModule } from '@angular/common';
import { Address } from '../../../model/address';
import { EstateDescribe } from '../../../model/estateDescribe';
import { EstateFeatures } from '../../../model/estateFeatures';
import { Estate } from '../../../model/estate';
import { ActivatedRoute } from '@angular/router';
import { EstateFactoryService } from '../../../_service/estate-factory/estate-factory.service';
import { ProgressBarComponent } from '../../progress-bar/progress-bar.component';
import { Coordinate } from '../../../model/coordinate';
import { PoiService } from '../../../_service/map-service/poi-service/poi.service';
import { EstateLocationFeatures } from '../../../model/estateLocationFeatures';
import { Observable, switchMap } from 'rxjs';
import { UploadPhotoService } from '../../../_service/rest-backend/upload-photo/upload-photo.service';
import { ToastrService } from 'ngx-toastr';
import { EstateItemDetailComponent } from '../../_estate-view/estate-item-detail/estate-item-detail.component';
import { EstateCreateService } from '../../../_service/rest-backend/estate-create/estate-create.service';
import { ValidateStepEstateCreateService } from '../../../_service/validate-step-create-estate/validate-step-estate-create.service';
import { EstateDataService } from '../../../_service/estate-data/estate-data.service';

@Component({
  selector: 'app-create-estates',
  imports: [
    SelectPlaceComponent, 
    DescriptionEstatesFormComponent, 
    EstatesFeaturesFormComponent,
    CommonModule,
    ProgressBarComponent,
    EstateItemDetailComponent
  ],
  templateUrl: './create-estates.component.html',
  styleUrl: './create-estates.component.scss'
})
export class CreateEstatesComponent implements OnInit{
  @ViewChild(DescriptionEstatesFormComponent) descriptionFormComponent!: DescriptionEstatesFormComponent;
  @ViewChild(EstatesFeaturesFormComponent) featuresFormComponent!: EstatesFeaturesFormComponent;
 


  address!: Address
  description!: EstateDescribe
  features!: EstateFeatures
  estate: Estate = {} as Estate
  additionalFields: any
  currentStep = 0;


  photos: string[] =[]
  filePhoto: File[] =[]
  readonly minPhotos = 3
  readonly maxPhotos = 10

  estateLocation: EstateLocationFeatures = {
    isNearPublicTransport: false,
    isNearSchool: false,
    isNearPark: false
  };

  steps: string[] = [
    'Step 1: Address',
    'Step 2: Description',
    'Step 3: Services',
    'Step 4: Preview'
  ]

  constructor(
    private readonly route: ActivatedRoute,
    private readonly estateFactory: EstateFactoryService,
    private readonly estateDataService: EstateDataService,
    private readonly poiService: PoiService,
    private readonly uploadPhotosService: UploadPhotoService,
    private readonly toastrService: ToastrService,
    private readonly createEstateService:EstateCreateService,
    private readonly validateStepService: ValidateStepEstateCreateService

  ){}
  

  onPhotosChanged(photos:string[]): void {
    this.photos = photos
  }

  onFileChanged(photos:File[]){
    this.filePhoto= photos
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.estate.type = params['type'] || 'For Sale';
    });
    
    this.address = this.estateDataService.getAddress()
    this.description = this.estateDataService.getDescription()
    
    this.additionalFields = this.estateDataService.getAdditionalFields()
    this.features = this.estateDataService.getFeatures() 
  }

  nextStep() {
    if(this.currentStep<3)
    {
      const state = this.currentStepValid()
      if(state == "")
        this.currentStep++
      else
        this.toastrService.warning(state);
      
    }
    if(this.currentStep==3)
      this.estate = this.createEstate()
  }

  checkPhoto() {
    throw new Error('Method not implemented.');
  }

  previousStep() {
    if (this.currentStep > 0) {
      this.currentStep--;
    }
  }

  isStepCompleted(step: number) {
    return this.currentStep >= step;
  }

  onAddressOut(event: Address){
    this.address = event
    this.estateDataService.setAddress(this.address)

    this.estate.address=this.address
  }



  currentStepValid(): string {

    switch (this.currentStep) {
      case 0:
        return this.validateStepService.checkStep1(this.address) 
      case 1: 
        return this.validateStepService.checkStep2(
          this.descriptionFormComponent,
          this.photos.length,
          this.maxPhotos,
          this.minPhotos
        )       
      case 2:
        return this.validateStepService.checkStep3(this.featuresFormComponent.featuresForm)
      case 3:
        return "";
        
      default:
        return "General Error";
    }
  }

  onDescribeOut(event: any)
  {
    this.description = event.estate
    this.additionalFields= event.additionalFields
    this.estateDataService.setAdditionalFields(this.additionalFields)
    this.estateDataService.setDescription(this.description)
    
    this.estate.estateDescribe=this.description
  }

  onEstateFeatures(event: EstateFeatures){
    this.features = event
    this.estateDataService.setFeatures(this.features)
    this.estate.estateFeatures=this.features
  }

  submit(){
    this.searchPoi({lat:this.address.latitude!, lon:this.address.longitude!}).subscribe((response:any)=>{
     this.estate=this.createEstate()
     console.log(this.estate)
    })

    this.createEstateService.createEstate(this.estate).subscribe((response:any)=>{
      this.uploadPhotos(response)

      
    }) 
  }

  uploadPhotos(id: any) {
    this.uploadPhotosService.uploadPhotos(id, this.filePhoto).subscribe({
      next:(response)=>{
        console.log(response)
        
        
      }
    })
  }

  createEstate(): Estate {
    this.estate.address = this.estateDataService.getAddress()
    this.estate.estateFeatures = this.features
    this.estate.estateDescribe = this.description
    this.estate.estateLocationFeatures= this.estateLocation
      
    return this.estateFactory.createEstate(
      this.estate,
      this.additionalFields
    );
  }


  searchPoi(coordinate:Coordinate): Observable<any>{
      const categories =['public_transport.bus', 'building.school','leisure.park']
      return this.poiService.searchPOI(coordinate, categories).pipe(
        switchMap((response: any) => {
          if (response?.features) {
            console.log(response)
            response.features.forEach((feature: any) => {
              if (feature.properties.categories.includes('public_transport.bus')) {
                this.estateLocation.isNearPublicTransport = true;
              }
              if (feature.properties.categories.includes('building.school')) {
                this.estateLocation.isNearSchool = true;
              }
              if (feature.properties.categories.includes('leisure.park')) {
                this.estateLocation.isNearPark = true;
              }
            });
          } else {
            console.error('Unexpected response format:', response);
          }
          return new Observable(observer => {
            observer.next(response);
            observer.complete();
          });
        })
      );
    }
}

