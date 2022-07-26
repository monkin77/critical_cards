import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { interval, Subject, take, takeUntil } from 'rxjs';
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
  edit: boolean = false;

  private _unsubscribeAll: Subject<any> = new Subject<any>();

  public session?: RetroSession;

  constructor(
    route: ActivatedRoute,
    private router: Router,
    private cardsApi: CardsApiService,
    private retroSessionService: RetroSessionService
  ) {
    this.router = router;
    this.sessionId = parseInt(route.snapshot.paramMap.get('id')!);
    if (this.sessionId == null) router.navigate(['pageNotFound']);
    this.cardsApi = cardsApi;
  }

  private updateSessionData() {
    this.retroSessionService
      .getSession(this.sessionId)
      .pipe(take(1))
      .subscribe({
        next: (session: RetroSession) => {
          this.session = session;
        },
        error: (err: any) => {
          this.router.navigate(['pageNotFound']);
        },
      });
  }

  ngOnInit(): void {
    this.updateSessionData();
    interval(10000)
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(() => this.updateSessionData());
  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next(null);
    this._unsubscribeAll.complete();
  }

  back() {
    this.router.navigate(['..']);
  }

  add_lane() {
    let lane = {
      id: this.session!.lanes.length,
      name: 'lane name',
      color: '#ffffff',
      cards: [],
    };

    this.session?.lanes.push(lane);
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
