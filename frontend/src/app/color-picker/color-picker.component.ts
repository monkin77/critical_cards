import { AfterViewChecked, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ColorService } from '../color.service';


@Component({
  selector: 'app-color-picker',
  templateUrl: './color-picker.component.html',
  styleUrls: ['./color-picker.component.css']
})
export class ColorPickerComponent implements OnInit {

  @ViewChild("colorpicker")
  public colorPicker!: ElementRef<HTMLDivElement>;

  @Input("color")
  public hex: String = "#ff0000";

  public visible = false;

  public hue: string = "210";
  public color: string = "";

  constructor(private colorService: ColorService) {}

  ngOnInit(): void {

  }

  public pickColor(event: MouseEvent) {
   event.stopPropagation();
  }

  public show() {
    let str = this.color;
    console.log(str);
    this.visible = !this.visible;

    if(this.visible){
      this.colorPicker.nativeElement.style.display = "block";
    }
    else{
      this.colorPicker.nativeElement.style.display = "none";
    }

    return this.rgba2hex(this.color);
  }

  public rgba2hex(rgba_str: string) {
    const rgba = rgba_str.replace(/^rgba?\(|\s+|\)$/g, '').split(',');

    const hex = `#${((1 << 24) + (parseInt(rgba[0]) << 16) + (parseInt(rgba[1]) << 8) + parseInt(rgba[2])).toString(16).slice(1)}`;
    return hex;
  }
}


