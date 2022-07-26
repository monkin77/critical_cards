import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { RetroLane } from 'src/app/DTOs/retro-lane';
import { ColorService } from 'src/app/color.service';
import { ColorPickerComponent } from 'src/app/color-picker/color-picker.component';
import { CardsApiService } from 'src/app/cards-api.service';

@Component({
  selector: 'app-retro-lane',
  templateUrl: './retro-lane.component.html',
  styleUrls: ['./retro-lane.component.scss'],
})
export class RetroLaneComponent implements OnInit {

  @ViewChild('color_picker')
  private colorPicker!: ColorPickerComponent;

  @Input('lane-data')
  public data!: RetroLane;

  @Input('sessionId')
  public sessionId!: number;

  public dark = false;

  constructor(
    private readonly colorService: ColorService,
    private cardsApi: CardsApiService,
    ) {}

  ngOnInit(): void {
    this.updateLaneMode();
  }

  updateLaneMode(): void {
    const rgb = this.colorService.hexToRgb(this.data.color);
    this.dark = rgb ? this.colorService.perceptiveLuminance(rgb) < 0.5 : false;
  }

  addCard() {
    const card = { text: 'Write here', color: '#c01722' };
    this.data.cards.push(Object.assign({}, card, { id: -1, votes: 0 }));

    this.cardsApi.createCard(card, this.sessionId, this.data.id);
  }

  public pickColor(event: MouseEvent): void {
    this.data.color = this.colorPicker.show();
    this.updateLaneMode();
    event.stopPropagation();
  }
}
