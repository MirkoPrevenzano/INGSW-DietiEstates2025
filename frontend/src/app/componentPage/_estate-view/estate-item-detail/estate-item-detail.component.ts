import {  Component, inject, Input, OnInit } from '@angular/core';
import { RealEstateCreation } from '../../../model/request/realEstateCreation';
import { EstateViewDescriptionComponent } from '../estate-view-description/estate-view-description.component';
import { EstateViewFeaturesComponent } from '../estate-view-features/estate-view-features.component';
import { EstateViewMapComponent } from '../estate-view-map/estate-view-map.component';
import { ImageSliderComponent } from '../../../componentCustom/image-slider/image-slider.component';
import { RealEstateForRentCreation } from '../../../model/request/realEstateForRentCreation';
import { RealEstateForSellCreation } from '../../../model/request/realEstateForSellCreation';
import { RentEstateViewComponent } from '../rent-estate-view/rent-estate-view.component';
import { SellEstateViewComponent } from '../sell-estate-view/sell-estate-view.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { NotFoundComponent } from '../../../componentCustom/not-found/not-found.component';
import { ToastrService } from 'ngx-toastr';
import { EstateViewAgentInfoComponent } from '../estate-view-agent-info/estate-view-agent-info.component';
import { AgentPublicInfo } from '../../../model/response/support/agentPublicInfo';
import { PhotoResult } from '../../../model/response/photoResult';
import { RealEstateCompleteInfo } from '../../../model/response/realEstateCompleteInfo';
import { HandleNotifyService } from '../../../_service/handle-notify.service';
import { RealEstateService } from '../../../rest-backend/real-estate/real-estate.service';

@Component({
  selector: 'app-estate-item',
  imports: [
    EstateViewDescriptionComponent,
    EstateViewFeaturesComponent,
    EstateViewMapComponent,
    ImageSliderComponent,
    RentEstateViewComponent,
    SellEstateViewComponent,
    CommonModule,
    NotFoundComponent,
    EstateViewAgentInfoComponent
  ],
  templateUrl: './estate-item-detail.component.html',
  styleUrl: './estate-item-detail.component.scss'
})
export class EstateItemDetailComponent implements OnInit{

  
  route = inject(ActivatedRoute)
  realEstateService = inject(RealEstateService)
  notifyService = inject(ToastrService)
  handleError = inject(HandleNotifyService)
  notFound404=false
  @Input() estate!: RealEstateCreation;
  @Input() agentInfo!:AgentPublicInfo
  @Input() photos: string[] = [];
  @Input() realEstateId!:number

  sliderConfig = {
    slidesToShow: 1,
    slidesToScroll: 1,
    dots: true,
    infinite: true,
    autoplay: true,
    autoplaySpeed: 2000
  };

  ngOnInit(): void {
    if(Number(this.route.snapshot.paramMap.has('id'))){
      this.realEstateId=Number(this.route.snapshot.paramMap.get('id'))
      this.loadEstate()
    }
  }

  
  
  loadEstate() {
    this.realEstateService.getEstateInfo(this.realEstateId).subscribe({
      next: (result:RealEstateCompleteInfo) => {
        console.log(result)
        this.estate = result.realEstateCreationDto; // Assegna il risultato alla proprietà `estate`
        this.agentInfo = result.agentPublicInfoDto
      },
      error: (err) => {
        if (err?.error.status == 404)
          this.notFound404 = true
        else
          this.handleError.showMessageError(err.error)
      }
    });
    
    this.retrievePhotos()
    
  }

  retrievePhotos() {
    this.realEstateService.getPhotos(this.realEstateId).subscribe({
      next: (photos:PhotoResult[]) => {
        this.photos = photos.map(photo => `data:image/${photo.contentType};base64,${photo.photoValue}`);
      },
      error: (err) => {
        this.handleError.showMessageError(err.error)
      }
    });
  }



  isEstateRent(estate: RealEstateCreation):estate is RealEstateForRentCreation  {
    return Object.hasOwn(estate,"contractYears")
    
  }

  isEstateSell(estate: RealEstateCreation): estate is RealEstateForSellCreation {
    return Object.hasOwn(estate, "notaryDeedState")

  }

  isNotFound() {
    return this.notFound404
  }

  
  
}


