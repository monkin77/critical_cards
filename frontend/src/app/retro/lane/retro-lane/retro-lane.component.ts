import { Component, Input, OnInit } from '@angular/core';
import { RetroLane } from 'src/app/DTOs/retro-lane';

@Component({
  selector: 'app-retro-lane',
  templateUrl: './retro-lane.component.html',
  styleUrls: ['./retro-lane.component.scss']
})
export class RetroLaneComponent implements OnInit {

  @Input("lane-data")
  public data!: RetroLane;


  constructor() { }

  ngOnInit(): void {
  }

}
