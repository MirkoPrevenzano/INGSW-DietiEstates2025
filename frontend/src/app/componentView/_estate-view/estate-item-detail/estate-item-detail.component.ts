import {  Component, inject, Input, OnInit } from '@angular/core';
import { Estate } from '../../../model/estate';
import { EstateViewDescriptionComponent } from '../estate-view-description/estate-view-description.component';
import { EstateViewFeaturesComponent } from '../estate-view-features/estate-view-features.component';
import { EstateViewMapComponent } from '../estate-view-map/estate-view-map.component';
import { ImageSliderComponent } from '../../image-slider/image-slider.component';
import { EstateRent } from '../../../model/estateRent';
import { EstateSell } from '../../../model/estateSell';
import { RentEstateViewComponent } from '../rent-estate-view/rent-estate-view.component';
import { SellEstateViewComponent } from '../sell-estate-view/sell-estate-view.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { UploadPhotoService } from '../../../_service/rest-backend/upload-photo/upload-photo.service';
import { GetEstateDetailService } from '../../../_service/rest-backend/get-estate-detail.service';
import { ReadPhotoService } from '../../../_service/read-photo/read-photo.service';
import { NotFoundComponent } from '../../not-found/not-found.component';

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
    NotFoundComponent
  ],
  templateUrl: './estate-item-detail.component.html',
  styleUrl: './estate-item-detail.component.scss'
})
export class EstateItemDetailComponent implements OnInit{

  
  route = inject(ActivatedRoute)
  uploadPhotosService = inject(UploadPhotoService)
  estateService = inject(GetEstateDetailService)
  readPhoto = inject(ReadPhotoService)
  notFound404=false
  @Input() estate!: Estate;
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
    this.estateService.getEstateInfo(this.realEstateId).subscribe({
      next: (result) => {
        
        this.estate = result; // Assegna il risultato alla proprietà `estate`
        console.log(result)
      },
      error: (err) => {
        if(err.code==404)
          this.notFound404=true
        console.error("Errore durante il caricamento dell'immobile:", err);
      }
    });
    
    this.retrievePhotos()
    
  }
//questo va
/*
    retrievePhotos() {
      this.uploadPhotosService.getPhotos(this.realEstateId).subscribe({
        next: (blob: Blob) => {
          this.convertBlobToBase64(blob).then((base64Photo: string) => {
            this.photos = [base64Photo]; // Assegna la foto convertita come array
            console.log("Foto caricata:", this.photos);
          });
        },
        error: (err) => {
          console.error("Errore durante il caricamento della foto:", err);
        }
      });
    }
    
    private convertBlobToBase64(blob: Blob): Promise<string> {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onloadend = () => {
          const base64Data = reader.result as string;
          const mimeType = blob.type || 'image/jpg'; // Usa il tipo MIME del Blob o un valore predefinito
          resolve(`data:${mimeType};base64,${base64Data.split(',')[1]}`);
        };
        reader.onerror = reject;
        reader.readAsDataURL(blob);
      });
     }
*/

//ricevo una foto come stringa
/*retrievePhotos() {
  this.uploadPhotosService.getPhotos(this.realEstateId).subscribe({
    next: (base64Photo:string) => {
      this.photos = [`data:image/jpeg;base64,${base64Photo}`]; // Aggiungi il prefisso per il tipo MIME
      console.log("Foto caricata nello slider:", this.photos);
    },
    error: (err) => {
      console.error("Errore durante il caricamento della foto:", err);
    }
  });
}*/


//versione con la lista di foto
retrievePhotos() {
  this.uploadPhotosService.getPhotos(this.realEstateId).subscribe({
    next: (base64Photo:string[]) => {
      this.photos = base64Photo.map(photo => `data:image/jpeg;base64,${photo}`);
      console.log("Foto caricate nello slider:", this.photos);
    },
    error: (err) => {
      console.error("Errore durante il caricamento della foto:", err);
    }
  });
}



  isEstateRent(estate: Estate):estate is EstateRent  {
    return Object.hasOwn(estate,"contractYears")
    
  }

  isEstateSell(estate: Estate): estate is EstateSell {
    return Object.hasOwn(estate, "notaryDeedState")

  }

  isNotFound() {
    return this.notFound404
  }

  
  
}


