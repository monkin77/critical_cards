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

  add_card() {
    let card = {
      id : this.data.cards.length + 1,
      text :"text",
      color : "#c01722",
      votes :  0
    }
    
    this.data.cards.push(card);
  }

  // TODO pallete icon change color
}
