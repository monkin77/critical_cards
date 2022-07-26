import { Component, OnDestroy, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { Subject, takeUntil } from 'rxjs';
import { RetroLane } from 'src/app/DTOs/retro-lane';
import { RetroCard } from '../DTOs/retro-card';
import { RetroSession } from '../DTOs/retro-session';
import { RetroSessionService } from '../retro-session.service';
import { CardsApiService } from '../cards-api.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-retro',
  templateUrl: './retro.component.html',
  styleUrls: ['./retro.component.scss'],
})

export class RetroComponent implements OnInit, OnDestroy {
  sessionId: number;
  cardsApi: CardsApiService;
  edit: boolean = false;
  router: Router;

  private _unsubscribeAll: Subject<any> = new Subject<any>();

  public session?: RetroSession;

  private card1: RetroCard = {id: 0, text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', color: '#b61b23', votes: 0};
  private card2: RetroCard = {id: 1, text: 'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', color: '#ffffff', votes: 2};
  private card3: RetroCard = {id: 2, text: 'Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.', color: '#00ffff', votes: 4};
  private card4: RetroCard = {id: 3, text: 'Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', color: '#00b612', votes: 6};

  private cards = [this.card1,this.card2,this.card3,this.card4];

  public lane_data1: RetroLane = {id: 0, name: 'Lane Name 1', color: '#FAEBD7', cards: this.cards}
  public lane_data2: RetroLane = {id: 1, name: 'Lane Name 2', color: '#777777', cards: this.cards}
  public lane_data3: RetroLane = {id: 2, name: 'Lane Name 3', color: '#444123', cards: this.cards}
  public lane_data4: RetroLane = {id: 3, name: 'Lane Name 4', color: '#345654', cards: this.cards}

  constructor(
    route: ActivatedRoute,
    router: Router,
    cardsApi: CardsApiService,
    private retroSessionService: RetroSessionService
  ) {
    this.router = router;
    this.sessionId = parseInt(route.snapshot.paramMap.get('id')!);
    if (this.sessionId == null) router.navigate(['pageNotFound']);
    this.cardsApi = cardsApi;
  }

  ngOnInit(): void {
    this.retroSessionService.getSessions()
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((session: RetroSession) => {
        this.session = session;
        console.log(this.session);
      })
  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next(null);
    this._unsubscribeAll.complete();
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

  saveName() {
    this.edit = false;

    this.cardsApi
      .updateRetro(this.sessionId, { name: this.session!.name })
      .subscribe((response: HttpResponse<any>) => {
        if (response.status != 200) {
          window.alert('Unable to edit name!');
          return;
        }
      });
  }
}
