import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RetroSession } from '../DTOs/retro-session';

@Component({
  selector: 'app-retro',
  templateUrl: './retro.component.html',
  styleUrls: ['./retro.component.scss'],
})
export class RetroComponent implements OnInit {
  sessionId: String | null;
  retroSession: RetroSession | null = null;

  constructor(route: ActivatedRoute, router: Router) {
    this.sessionId = route.snapshot.paramMap.get('id');
    if (this.sessionId == null) router.navigate(['pageNotFound']);
    else {
      // TODO get retro from service
      this.retroSession = { id: -1, name: 'Mock Retro', lanes: [] };
    }
  }

  ngOnInit(): void {}

  editRetroName() {
    console.log('TODO, Should edit');
  }

  back() {
    console.log('TODO, Should navigate back');
  }

  addLane() {
    console.log('TODO, Should add lane');
  }
}
