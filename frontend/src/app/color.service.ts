import { getLocaleExtraDayPeriodRules } from '@angular/common';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ColorService {

  constructor() { }

  public hexToRgb(hex: String): number[] | null {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex.toString());
    if(result){
        var r = parseInt(result[1], 16);
        var g = parseInt(result[2], 16);
        var b = parseInt(result[3], 16);
        return [r, g, b];
    }

    return null;
  }

  // For further development, please create a Color interface
  public perceptiveLuminance(rgb: number[]): number {
    return (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2] ) / 255;
  }
}
