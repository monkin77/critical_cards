import { Component, Input, OnInit } from '@angular/core';
import { RetroLane } from 'src/app/DTOs/retro-lane';
import { ColorService } from 'src/app/color.service';

@Component({
  selector: 'app-retro-lane',
  templateUrl: './retro-lane.component.html',
  styleUrls: ['./retro-lane.component.scss'],
})
export class RetroLaneComponent implements OnInit {
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
}
