import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetroComponent } from './retro.component';

describe('RetroComponent', () => {
  let component: RetroComponent;
  let fixture: ComponentFixture<RetroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RetroComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(RetroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display name', () => {
    const testName = 'testName';
    component.retroSession = { id: -1, name: testName, lanes: [] };
    fixture.detectChanges();
    const retroElement: HTMLElement = fixture.debugElement.nativeElement;
    const headerName = retroElement.querySelector('h2')!;
    expect(headerName.textContent).toEqual(testName);
  });
});
