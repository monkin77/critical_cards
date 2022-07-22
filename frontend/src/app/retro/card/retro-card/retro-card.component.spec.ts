import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetroCardComponent } from './retro-card.component';

describe('RetroCardComponent', () => {
  let component: RetroCardComponent;
  let fixture: ComponentFixture<RetroCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RetroCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RetroCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
