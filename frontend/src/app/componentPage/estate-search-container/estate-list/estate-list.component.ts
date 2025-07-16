import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EstatePreview } from '../../../model/estatePreview';
import { EstateItemPreviewComponent } from '../../_estate-view/estate-item-preview/estate-item-preview.component';

@Component({
  selector: 'app-estate-list',
  imports: [
    EstateItemPreviewComponent,
    CommonModule
  ],
  templateUrl: './estate-list.component.html',
  styleUrl: './estate-list.component.scss'
})
export class EstateListComponent{

  @Input() listEstatePreview!: EstatePreview[]
  @Input() listRealEstateId!: number[]
  
  
}
