import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { SplashComponent } from './splash/splash.component';
import { PokerComponent } from './poker/poker.component';
import { RetroComponent } from './retro/retro.component';
import { SessionComponent } from './session/session.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { RetroCardComponent } from './retro/card/retro-card/retro-card.component';
import { RetroLaneComponent } from './retro/lane/retro-lane/retro-lane.component';

@NgModule({
  declarations: [
    AppComponent,
    SplashComponent,
    PokerComponent,
    RetroComponent,
    SessionComponent,
    PageNotFoundComponent,
    RetroCardComponent,
    RetroLaneComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: '', component: SplashComponent, title: 'Critical Cards'},
      {path: 'poker', component: PokerComponent, title: 'Critical Cards - Planning Poker'},
      {path: 'retro/:id', component: RetroComponent, title: 'Critical Cards - Retrospective'},
      {path: '**', component: PageNotFoundComponent}
    ]),
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
