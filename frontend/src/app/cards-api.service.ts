import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Observable, throwError } from 'rxjs';
import {SimpleSession} from "./DTOs/simple-session";

@Injectable({
  providedIn: 'root'
})
export class CardsApiService {

  constructor(private apiClient: HttpClient) {
  }

  createRetro(data: SimpleSession) {
    return this.apiClient.post<any>("http://localhost:8080/retro", data, {observe: 'response'});
  }

  updateRetro(id: number, data: SimpleSession) {
    let link = "http://localhost:8080/retro/" + id;
    return this.apiClient.put<any>(link, data, {observe: 'response'});
  }
}
