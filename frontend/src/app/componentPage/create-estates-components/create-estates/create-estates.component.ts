import {  Component, OnInit, ViewChild } from '@angular/core';
import { DescriptionEstatesFormComponent } from '../description-estates-form/description-estates-form.component';
import { EstatesFeaturesFormComponent } from '../estates-features-form/estates-features-form.component';
import { SelectPlaceComponent } from '../select-place/select-place.component';
import { CommonModule } from '@angular/common';
import { Address } from '../../../model/request/support/address';
import { RealEstateMainFeatures } from '../../../model/request/support/realEstateMainFeatures';
import { RealEstateBooleanFeatures } from '../../../model/request/support/realEstateBooleanFeatures';
import { RealEstateCreation } from '../../../model/request/realEstateCreation';
import { ActivatedRoute, Router } from '@angular/router';
import { EstateFactoryService } from '../../../_service/estate-factory/estate-factory.service';
import { ProgressBarComponent } from '../../../componentCustom/progress-bar/progress-bar.component';
import { Coordinate } from '../../../model/coordinate';
import { PoiService } from '../../../_service/map-service/poi-service/poi.service';
import { RealEstateLocationFeatures } from '../../../model/request/support/realEstateLocationFeatures';
import { Observable, switchMap } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { EstateItemDetailComponent } from '../../_estate-view/estate-item-detail/estate-item-detail.component';
import { ValidateStepEstateCreateService } from '../../../_service/validate-step-create-estate/validate-step-estate-create.service';
import { EstateDataService } from '../../../_service/estate-data/estate-data.service';
import { HandleNotifyService } from '../../../_service/handle-notify.service';
import { RealEstateService } from '../../../rest-backend/real-estate/real-estate.service';
import { AgentPublicInfo } from '../../../model/response/support/agentPublicInfo';
import { AgentService } from '../../../rest-backend/agent/agent.service';

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
  description!: RealEstateMainFeatures
  features!: RealEstateBooleanFeatures
  agentPublicInfo!: AgentPublicInfo
  estate: RealEstateCreation = {} as RealEstateCreation
  additionalFields: any
  currentStep = 0;


  photos: string[] =[]
  filePhoto: File[] =[]
  readonly minPhotos = 3
  readonly maxPhotos = 10

  estateLocation: RealEstateLocationFeatures = {
    nearPublicTransport: false,
    nearSchool: false,
    nearPark: false
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
    private readonly toastrService: ToastrService,
    private readonly realEstateService:RealEstateService,
    private readonly validateStepService: ValidateStepEstateCreateService,
    private readonly router: Router,
    private readonly handleError:HandleNotifyService,
    private readonly agentService: AgentService

  ){
    agentService.getAgentPublicInfo().subscribe({
      next: (response: AgentPublicInfo) => {
        this.agentPublicInfo = response;
      }
    });
  }
  

  onPhotosChanged(photos:string[]): void {
    this.photos = photos
  }

  onFileChanged(photos:File[]){
    this.filePhoto= photos
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.estate.contractType = params['type'] ?? 'For Sale';
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

    this.estate.addressDto=this.address
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
    
    this.estate.realEstateMainFeaturesDto=this.description
  }

  onEstateFeatures(event: RealEstateBooleanFeatures){
    this.features = event
    this.estateDataService.setFeatures(this.features)
    this.estate.realEstateBooleanFeaturesDto=this.features
  }

  submit(){
    if(this.address.latitude && this.address.longitude)
      this.searchPoi({lat:this.address.latitude, lon:this.address.longitude}).subscribe(()=>{
        this.estate=this.createEstate()
      })

    this.realEstateService.createEstate(this.estate).subscribe({
      next: (response: any) => {
        this.uploadPhotos(response);
        setTimeout(()=>{
          this.toastrService.success("The estate has been created successfully!", "Success")
        },1000)
        this.router.navigateByUrl('/home/admin')
        
      },
      error: (err) => {
        this.handleError.showMessageError(err.error)
      }
    });
  }

  uploadPhotos(id: any) {
    this.realEstateService.uploadPhotos(id, this.filePhoto).subscribe({})
  }

  createEstate(): RealEstateCreation {
    this.estate.addressDto = this.estateDataService.getAddress()
    this.estate.realEstateBooleanFeaturesDto = this.features
    this.estate.realEstateMainFeaturesDto = this.description
    this.estate.realEstateLocationFeaturesDto= this.estateLocation
      
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
            response.features.forEach((feature: any) => {
              if (feature.properties.categories.includes('public_transport.bus')) {
                this.estateLocation.nearPublicTransport = true;
              }
              if (feature.properties.categories.includes('building.school')) {
                this.estateLocation.nearSchool = true;
              }
              if (feature.properties.categories.includes('leisure.park')) {
                this.estateLocation.nearPark = true;
              }
            });
          }
          return new Observable(observer => {
            observer.next(response);
            observer.complete();
          });
        })
      );
    }
}

