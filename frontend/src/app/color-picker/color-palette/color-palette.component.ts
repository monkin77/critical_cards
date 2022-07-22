import { Component, ViewChild, ElementRef, AfterViewInit, Input, Output, SimpleChanges, OnChanges, EventEmitter, HostListener } from '@angular/core';

@Component({
  selector: 'app-color-palette',
  templateUrl: './color-palette.component.html',
  styleUrls: ['./color-palette.component.css']
})
export class ColorPaletteComponent implements AfterViewInit, OnChanges {
  @Input()
  hue: string = "210";

  @Output()
  color: EventEmitter<string> = new EventEmitter(true);

  @ViewChild('canvas')
  canvas: ElementRef<HTMLCanvasElement> | null = null;

  private ctx: CanvasRenderingContext2D | null = null;

  private mousedown: boolean = false;

  public selectedPosition: { x: number; y: number } | null = null;

  ngAfterViewInit() {
    this.draw();
  }

  draw() {
    if (!this.ctx) {
      // @ts-ignore
      this.ctx = this.canvas.nativeElement.getContext('2d');
    }
    // @ts-ignore
    const width = this.canvas.nativeElement.width;
    // @ts-ignore
    const height = this.canvas.nativeElement.height;

    // @ts-ignore
    this.ctx.fillStyle = this.hue || 'rgba(255,255,255,1)';
    // @ts-ignore
    this.ctx.fillRect(0, 0, width, height);

    // @ts-ignore
    const whiteGrad = this.ctx.createLinearGradient(0, 0, width, 0);
    whiteGrad.addColorStop(0, 'rgba(255,255,255,1)');
    whiteGrad.addColorStop(1, 'rgba(255,255,255,0)');
    // @ts-ignore
    this.ctx.fillStyle = whiteGrad;
    // @ts-ignore
    this.ctx.fillRect(0, 0, width, height);
    // @ts-ignore
    const blackGrad = this.ctx.createLinearGradient(0, 0, 0, height);
    blackGrad.addColorStop(0, 'rgba(0,0,0,0)');
    blackGrad.addColorStop(1, 'rgba(0,0,0,1)');
    // @ts-ignore
    this.ctx.fillStyle = blackGrad;
    // @ts-ignore
    this.ctx.fillRect(0, 0, width, height);

    if (this.selectedPosition) {
      // @ts-ignore
      this.ctx.strokeStyle = 'white';
      // @ts-ignore

      this.ctx.fillStyle = 'white';
      // @ts-ignore

      this.ctx.beginPath();
      // @ts-ignore

      this.ctx.arc(this.selectedPosition.x, this.selectedPosition.y, 10, 0, 2 * Math.PI);
      // @ts-ignore

      this.ctx.lineWidth = 5;
      // @ts-ignore

      this.ctx.stroke();
      // @ts-ignore

    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['hue']) {
      this.draw();
      const pos = this.selectedPosition;
      if (pos) {
        this.color.emit(this.getColorAtPosition(pos.x, pos.y));
      }
    }
  }

  @HostListener('window:mouseup', ['$event'])
  onMouseUp(evt: MouseEvent) {
    this.mousedown = false;
  }

  onMouseDown(evt: MouseEvent) {
    this.mousedown = true;
    this.selectedPosition = { x: evt.offsetX, y: evt.offsetY };
    this.draw();
    this.color.emit(this.getColorAtPosition(evt.offsetX, evt.offsetY));
  }

  onMouseMove(evt: MouseEvent) {
    if (this.mousedown) {
      this.selectedPosition = { x: evt.offsetX, y: evt.offsetY };
      this.draw();
      this.emitColor(evt.offsetX, evt.offsetY);
    }
  }

  emitColor(x: number, y: number) {
    const rgbaColor = this.getColorAtPosition(x, y);
    this.color.emit(rgbaColor);
  }

  getColorAtPosition(x: number, y: number) {
    // @ts-ignore
    const imageData = this.ctx.getImageData(x, y, 1, 1).data;
    return 'rgba(' + imageData[0] + ',' + imageData[1] + ',' + imageData[2] + ',1)';
  }
}
