import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetroLaneComponent } from './retro-lane.component';

describe('RetroLaneComponent', () => {
  let component: RetroLaneComponent;
  let fixture: ComponentFixture<RetroLaneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RetroLaneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RetroLaneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
