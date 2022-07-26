import {AfterViewChecked, Component, ElementRef, Host, Input, OnInit, ViewChild} from '@angular/core';
import { ColorService } from '../color.service';


@Component({
  selector: 'app-color-picker',
  templateUrl: './color-picker.component.html',
  styleUrls: ['./color-picker.component.css']
})
export class ColorPickerComponent implements OnInit {

  @Input("color")
  public hex: String = "#ff0000";

  public visible = false;

  public hue: string = "210";
  public color: string = "";

  constructor(private colorService: ColorService) {

  }

  ngOnInit(): void {
    let rgb = this.colorService.hexToRgb(this.hex);
    let rgba_str;
    if (rgb) {
      rgba_str = "rgba(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + "," + 1 + ")";
    }
    this.color = String(rgba_str);
  }

  public pickColor(event: MouseEvent) {
   event.stopPropagation();
  }

  public show() {
    console.log(this.color);
    this.visible = !this.visible;
    return this.colorService.rgba2hex(this.color);
  }
}


