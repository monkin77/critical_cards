import { Component, Input,OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { CardsApiService } from '../cards-api.service';
import { RetroSession } from '../DTOs/retro-session';
import { HttpResponse } from '@angular/common/http';
@Component({
  selector: 'app-retro',
  templateUrl: './retro.component.html',
  styleUrls: ['./retro.component.scss']
})
export class RetroComponent implements OnInit {
  sessionId: number;
  @Input() retroSession: RetroSession | null = null;
  cardsApi: CardsApiService;
  edit: boolean = false;
  router: Router;
  cards$: any = [];


  constructor(
    route: ActivatedRoute,
    router: Router,
    cardsApi: CardsApiService
  ) {
    this.router = router;
    this.sessionId = parseInt(route.snapshot.paramMap.get('id')!);
    if (this.sessionId == null) router.navigate(['pageNotFound']);
    else {
      // TODO get retro from service
      this.retroSession = { id: -1, name: 'Mock Retro', lanes: [] };
    }
    this.cardsApi = cardsApi;
  }
  ngOnInit(): void {
    this.getAll();

  }

  getAll() {
    this.cardsApi.getAllData(this.sessionId.toString()).subscribe(result => {
      //Lane na linha 0 pré-definida pois não existem lanes criadas
      this.cards$ = result.body.lanes[0].cards
    })
  }
  back() {
    this.router.navigate([".."]);
  }

  addLane() {
    console.log('TODO, Should add lane');
  }

  editRetroName() {
    this.edit = true;
  }

  savename() {
    this.edit = false;

    this.cardsApi
      .updateRetro(this.sessionId, { name: this.retroSession!.name })
      .subscribe((response: HttpResponse<any>) => {
        if (response.status != 200) {
          window.alert('Unable to edit name!');
          return;
        }
      });
  }

}
