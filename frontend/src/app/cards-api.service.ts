import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Observable, Subject, throwError, tap } from 'rxjs';
import {SimpleSession} from "./DTOs/simple-session";
import {SimpleRetroCard} from "./DTOs/simple-retro-card";

@Injectable({
  providedIn: 'root'
})
export class CardsApiService {

  constructor(private apiClient: HttpClient) { }

  private _refreshrequired =  new Subject<void>();


  get Refreshrequired(){
    return this._refreshrequired;
  }
  updateRetro(id: number, data: SimpleSession) {
    let link = "http://localhost:8080/retro/" + id;
    return this.apiClient.put<any>(link, data, {observe: 'response'});
  }

  createRetro(data:SimpleSession) {
    return this.apiClient.post<any>("http://localhost:8080/retro", data,  {observe: 'response'});
  }

  createVote(data:SimpleRetroCard, bolvote: boolean,id: number) {
    if(bolvote){
      return this.apiClient.put<any>("http://localhost:8080/card/unvote/"+id, data,  {observe: 'response'}).pipe(
        tap(()=> {
          this.Refreshrequired.next();
        })
      );
    } else {
      return this.apiClient.put<any>("http://localhost:8080/card/vote/"+id, data,  {observe: 'response'}).pipe(
        tap(()=> {
          this.Refreshrequired.next();
        })
      );
    }
  }

  getAllData(idSession: String|null){
    return this.apiClient.get<any>("http://localhost:8080/retro/" + idSession, {observe: 'response'});
  }
}
