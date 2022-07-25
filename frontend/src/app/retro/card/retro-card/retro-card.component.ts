import { AfterViewChecked, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ColorService } from 'src/app/color.service';
import { RetroCard } from 'src/app/DTOs/retro-card';
import { ColorPickerComponent } from 'src/app/color-picker/color-picker.component'

@Component({
  selector: 'app-retro-card',
  templateUrl: './retro-card.component.html',
  styleUrls: ['./retro-card.component.scss']
})
export class RetroCardComponent implements AfterViewChecked, OnInit {

  @ViewChild('card_text')
  private textEdit!: ElementRef<HTMLInputElement>;

  @ViewChild('color_picker')
  private ColorPicker!: ColorPickerComponent;

  @Input('data')
  public data!: RetroCard;

  public text = '';
  public dark = false;
  public writemode = false;

  public picker_visible = false;

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

  public pickColor(event: MouseEvent): void {
    this.data.color = this.ColorPicker.show();
    event.stopPropagation();
  }

  public vote_action(event: MouseEvent): void {
    event.stopPropagation();
  }


  updateCardMode(): void {
    const rgb = this.colorService.hexToRgb(this.data.color);
    this.dark = rgb ? this.colorService.perceptiveLuminance(rgb) < 0.5 : false;
  }

}
