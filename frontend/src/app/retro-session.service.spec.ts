import { TestBed } from '@angular/core/testing';

import { RetroSessionService } from './retro-session.service';

describe('RetroSessionServiceService', () => {
  let service: RetroSessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RetroSessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
