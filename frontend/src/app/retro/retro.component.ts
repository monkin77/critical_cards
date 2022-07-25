import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CardsApiService } from '../cards-api.service';

@Component({
  selector: 'app-retro',
  templateUrl: './retro.component.html',
  styleUrls: ['./retro.component.scss']
})
export class RetroComponent implements OnInit {
  sessionId: String | null;
  cards$: any = [];


  constructor(route: ActivatedRoute, router: Router, private apiservice: CardsApiService) {
    this.sessionId = route.snapshot.paramMap.get("id");
    if (this.sessionId == null)
      router.navigate(['pageNotFound']);
  }

  ngOnInit(): void {
    this.getAll();

  }

  getAll() {
    this.apiservice.getAllData(this.sessionId).subscribe(result => {
      //Lane na linha 0 pré-definida pois não existem lanes criadas
      this.cards$ = result.body.lanes[0].cards
    })
  }

}
