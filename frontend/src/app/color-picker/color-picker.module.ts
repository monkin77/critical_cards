import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ColorSliderComponent } from './color-slider/color-slider.component';
import { ColorPaletteComponent } from './color-palette/color-palette.component';



@NgModule({
  declarations: [
    ColorSliderComponent,
    ColorPaletteComponent
  ],
  imports: [
    CommonModule
  ]
})
export class ColorPickerModule { }
