import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
    this.retroSessionService
      .getSessions()
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((session: RetroSession) => {
        this.session = session;
        console.log(this.session);
      });
  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next(null);
    this._unsubscribeAll.complete();
  }

  back() {
    this.router.navigate(['..']);
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
