import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-retro',
  templateUrl: './retro.component.html',
  styleUrls: ['./retro.component.scss']
})
export class RetroComponent implements OnInit {
  sessionId:String|null;

  constructor(route: ActivatedRoute, router:Router) {
    this.sessionId = route.snapshot.paramMap.get("id");
    if(this.sessionId==null)
      router.navigate(['pageNotFound']);

  }

  ngOnInit(): void {
  }

  editRetroName() {
    console.log("TODO, Should edit");
  }

}
