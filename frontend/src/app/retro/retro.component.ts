import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { RetroLane } from 'src/app/DTOs/retro-lane';
import { RetroCard } from '../DTOs/retro-card';

@Component({
  selector: 'app-retro',
  templateUrl: './retro.component.html',
  styleUrls: ['./retro.component.scss']
})
export class RetroComponent implements OnInit {
  sessionId:String|null;

  private card: RetroCard = {id: 0, text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', color: '#b61b23', votes: 0};
  
  private cards = [this.card,this.card];
  
  public lane_data: RetroLane = {id: 0, name: 'Lane Name', color: '#b61b23', cards: this.cards}

  constructor(route: ActivatedRoute, router:Router) {
    this.sessionId = route.snapshot.paramMap.get("id");
    if(this.sessionId==null)
      router.navigate(['pageNotFound']);

  }

  ngOnInit(): void {
  }

}
