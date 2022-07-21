import { Component, OnInit } from '@angular/core';
import {CardsApiService} from "../cards-api.service";
import {HttpResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-splash',
  templateUrl: './splash.component.html',
  styleUrls: ['./splash.component.scss']
})
export class SplashComponent implements OnInit {

  mode:Mode=Mode.MAIN;
  sessionId: string="";
  cardsApi:CardsApiService;

  constructor(cardsApi:CardsApiService, private router: Router) {
    this.cardsApi = cardsApi;
  }

  ngOnInit(): void {
  }

  isMain() {
    return this.mode==Mode.MAIN;
  }

  newPoker() {
    //not implemented yet
  }

  newRetro() {
    this.cardsApi.createRetro({name:"New Retrospective"})
      .subscribe((response: HttpResponse<any>) => {
        if (response.status != 201) {
          window.alert("Unable to create retrospective!");
          return;
        }
        let address = response.headers.get("Location");

        this.join(address?.split("/").pop());
      });
  }

  join(id:String|undefined) {
    console.log("joining: "+id);
    this.router.navigate(['retro/'+id]);
  }
}
export enum Mode{
  MAIN,
  NEW,
  JOIN
}
