import { AfterViewChecked, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ColorService } from 'src/app/color.service';
import { RetroCard } from 'src/app/DTOs/retro-card';
import { HttpResponse } from "@angular/common/http";
import { CardsApiService } from "../../../cards-api.service";
import { RetroComponent } from "../../retro.component";

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

  public text = '';
  public dark = false;
  public writemode = false;

  public vote = false;
  public mutex_flag = false;

  private openedWriteMode = false;

  constructor(private readonly colorService: ColorService, private apivote: CardsApiService, private retroComponent: RetroComponent) { }

  ngOnInit(): void {
    this.updateCardMode();
    localStorage.setItem("vote", this.vote.toString());
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
    event.stopPropagation();
  }

  public vote_action(event: MouseEvent): void {
    event.stopPropagation();

    if (!this.mutex_flag) {
      this.mutex_flag = true;
      this.apivote.createVote({ text: "New Text", color: "#ffffff" }, this.vote, this.data.id)
        .subscribe((response: HttpResponse<any>) => {
          if (response.ok) {
            this.vote = !this.vote;
            this.data.votes = response.body.votes;
          }
          this.mutex_flag = false;
        });
    }
  }

  updateCardMode(): void {
    const rgb = this.colorService.hexToRgb(this.data.color);
    this.dark = rgb ? this.colorService.perceptiveLuminance(rgb) < 0.5 : false;
  }

}

