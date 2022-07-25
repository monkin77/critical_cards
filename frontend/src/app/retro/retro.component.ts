import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RetroSession } from '../DTOs/retro-session';
import { CardsApiService } from '../cards-api.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-retro',
  templateUrl: './retro.component.html',
  styleUrls: ['./retro.component.scss'],
})
export class RetroComponent implements OnInit {
  sessionId: number;
  @Input() retroSession: RetroSession | null = null;
  cardsApi: CardsApiService;
  edit: boolean = false;

  constructor(
    route: ActivatedRoute,
    router: Router,
    cardsApi: CardsApiService
  ) {
    this.sessionId = parseInt(route.snapshot.paramMap.get('id')!);
    if (this.sessionId == null) router.navigate(['pageNotFound']);
    else {
      // TODO get retro from service
      this.retroSession = { id: -1, name: 'Mock Retro', lanes: [] };
    }
    this.cardsApi = cardsApi;
  }

  ngOnInit(): void {}

  back() {
    console.log('TODO, Should navigate back');
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
