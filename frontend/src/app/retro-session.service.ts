import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, tap, ReplaySubject } from 'rxjs';
import { RetroSession } from './DTOs/retro-session';

@Injectable({
  providedIn: 'root',
})
export class RetroSessionService {
  private _session: ReplaySubject<RetroSession> =
    new ReplaySubject<RetroSession>();

  constructor(private _httpClient: HttpClient) {}

  get session$(): Observable<RetroSession> {
    return this._session.asObservable();
  }

  public getSession(n: number): Observable<RetroSession> {
    return this._httpClient
      .get<RetroSession>('http://localhost:8080/retro/' + n)
      .pipe(
        tap((session) => {
          this._session.next(session);
        })
      );
  }
}
