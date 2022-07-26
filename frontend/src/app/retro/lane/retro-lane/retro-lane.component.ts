import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { RetroLane } from 'src/app/DTOs/retro-lane';
import { ColorService } from 'src/app/color.service';
import { ColorPickerComponent } from 'src/app/color-picker/color-picker.component';

@Component({
  selector: 'app-retro-lane',
  templateUrl: './retro-lane.component.html',
  styleUrls: ['./retro-lane.component.scss'],
})
export class RetroLaneComponent implements OnInit {

  @ViewChild('color_picker')
  private colorPicker!: ColorPickerComponent;

  @Input('lane-data')
  public data!: RetroLane;

  public dark = false;

  constructor(private readonly colorService: ColorService) {}

  ngOnInit(): void {
    this.updateLaneMode();
  }

  updateLaneMode(): void {
    const rgb = this.colorService.hexToRgb(this.data.color);
    this.dark = rgb ? this.colorService.perceptiveLuminance(rgb) < 0.5 : false;
  }

  add_card() {
    let card = {
      id: this.data.cards.length + 1,
      text: 'text',
      color: '#c01722',
      votes: 0,
    };

    this.data.cards.push(card);
  }

  public pickColor(event: MouseEvent): void {
    this.data.color = this.colorPicker.show();
    this.updateLaneMode();
    event.stopPropagation();
  }
}
