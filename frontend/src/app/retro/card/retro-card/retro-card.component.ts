import { AfterViewChecked, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ColorService } from 'src/app/color.service';
import { RetroCard } from 'src/app/DTOs/retro-card';

@Component({
  selector: 'app-retro-card',
  templateUrl: './retro-card.component.html',
  styleUrls: ['./retro-card.component.scss']
})
export class RetroCardComponent implements AfterViewChecked, OnInit {

  @ViewChild('card_text')
  private textEdit!: ElementRef<HTMLInputElement>;

  @Input('data')
  public data!: RetroCard;

  public dark = false;
  public writemode = false;

  public vote = false;

  private openedWriteMode = false;

  constructor(private readonly colorService: ColorService) {}

  ngOnInit(): void {
    this.updateCardMode();
  }

  ngAfterViewChecked() {
    if (this.openedWriteMode && this.textEdit) {
      this.openedWriteMode = false;
      this.textEdit.nativeElement.focus();
    }
  }

  public setWriteMode(mode: boolean): void {
    this.openedWriteMode = !this.writemode && mode;
    this.writemode = mode;
  }

  public edit_text(event: Event): void {
    event.stopPropagation();
    this.data.text = this.textEdit.nativeElement.value;
  }

  public pickColor(event: MouseEvent): void {
    event.stopPropagation();
  }

  public vote_action(event: MouseEvent): void {
    event.stopPropagation();
  }

  public delete(event: MouseEvent) : void{
    event.stopPropagation();
  }


  updateCardMode(): void {
    const rgb = this.colorService.hexToRgb(this.data.color);
    this.dark = rgb ? this.colorService.perceptiveLuminance(rgb) < 0.5 : false;
  }
}
