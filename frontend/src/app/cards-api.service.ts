import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, throwError, tap } from 'rxjs';
import { SimpleSession } from './DTOs/simple-session';
import { SimpleRetroCard } from './DTOs/simple-retro-card';
import {SimpleRetroLane} from "./DTOs/simple-retro-lane";

@Injectable({
  providedIn: 'root',
})
export class CardsApiService {
  constructor(private apiClient: HttpClient) {}

  private _refreshrequired = new Subject<void>();

  get Refreshrequired() {
    return this._refreshrequired;
  }

  updateRetro(id: number, data: SimpleSession) {
    let link = 'http://localhost:8080/retro/' + id;
    return this.apiClient.put<any>(link, data, { observe: 'response' });
  }

  createRetro(data: SimpleSession) {
    return this.apiClient.post<any>('http://localhost:8080/retro', data, {
      observe: 'response',
    });
  }

  createVote(data: SimpleRetroCard, bolvote: boolean, id: number) {
    if (bolvote) {
      return this.apiClient
        .put<any>('http://localhost:8080/card/unvote/' + id, data, {
          observe: 'response',
        })
        .pipe(
          tap(() => {
            this.Refreshrequired.next();
          })
        );
    } else {
      return this.apiClient
        .put<any>('http://localhost:8080/card/vote/' + id, data, {
          observe: 'response',
        })
        .pipe(
          tap(() => {
            this.Refreshrequired.next();
          })
        );
    }
  }

  getAllData(idSession: String) {
    return this.apiClient.get<any>(`http://localhost:8080/retro/${idSession}`, {observe: 'response'});
  }

  createLane(data: SimpleRetroLane, idSession: String) {
    return this.apiClient.post<any>(`http://localhost:8080/retro/${idSession}/lane/`, data,  {observe: 'response'});
  }

  removeLane(idSession:String | null, idLane:String) {
    return this.apiClient.delete<any>(`http://localhost:8080/retro/${idSession}/lane/${idLane}`,  {observe: 'response'});
  }

  createCard(data: SimpleRetroCard, idSession: number, idLane: number) {
    console.log(`http://localhost:8080/retro/${idSession}/lane/${idLane}/card`)
    return this.apiClient.post<any>(`http://localhost:8080/retro/${idSession}/lane/${idLane}/card`, data,  {observe: 'response'});
  }

  updateTextCard(data: SimpleRetroCard, id:number) {
    console.log("update card")
    return this.apiClient
    .put<any>('http://localhost:8080/card/' + id, data, {
      observe: 'response',
    })
    .pipe(
      tap(() => {
        this.Refreshrequired.next();
      })
    );
  }
}
