import {
  Component,
  ViewChild,
  ElementRef,
  AfterViewInit,
  Output,
  HostListener,
  EventEmitter,
} from '@angular/core'

@Component({
  selector: 'app-color-slider',
  templateUrl: './color-slider.component.html',
  styleUrls: ['./color-slider.component.css'],
})
export class ColorSliderComponent implements AfterViewInit {
  @ViewChild('canvas')
  canvas: ElementRef<HTMLCanvasElement> | null = null

  @Output()
  color: EventEmitter<string> = new EventEmitter()

  private ctx: CanvasRenderingContext2D | null = null
  private mousedown: boolean = false
  private selectedHeight: number | null = null

  ngAfterViewInit() {
    this.draw()
  }

  draw() {
    if (!this.ctx) {
      // @ts-ignore
      this.ctx = this.canvas.nativeElement.getContext('2d')
    }
    // @ts-ignore
    const width = this.canvas.nativeElement.width
    // @ts-ignore
    const height = this.canvas.nativeElement.height
    // @ts-ignore
    this.ctx.clearRect(0, 0, width, height)
    // @ts-ignore
    const gradient = this.ctx.createLinearGradient(0, 0, 0, height)
    gradient.addColorStop(0, 'rgba(255, 0, 0, 1)')
    gradient.addColorStop(0.17, 'rgba(255, 255, 0, 1)')
    gradient.addColorStop(0.34, 'rgba(0, 255, 0, 1)')
    gradient.addColorStop(0.51, 'rgba(0, 255, 255, 1)')
    gradient.addColorStop(0.68, 'rgba(0, 0, 255, 1)')
    gradient.addColorStop(0.85, 'rgba(255, 0, 255, 1)')
    gradient.addColorStop(1, 'rgba(255, 0, 0, 1)')

    // @ts-ignore
    this.ctx.beginPath()
    // @ts-ignore
    this.ctx.rect(0, 0, width, height)

    // @ts-ignore
    this.ctx.fillStyle = gradient
    // @ts-ignore
    this.ctx.fill()
    // @ts-ignore
    this.ctx.closePath()

    if (this.selectedHeight) {
      // @ts-ignore
      this.ctx.beginPath()
      // @ts-ignore
      this.ctx.strokeStyle = 'white'
      // @ts-ignore
      this.ctx.lineWidth = 5
      // @ts-ignore
      this.ctx.rect(0, this.selectedHeight - 5, width, 10)
      // @ts-ignore
      this.ctx.stroke()
      // @ts-ignore
      this.ctx.closePath()
    }
  }

  @HostListener('window:mouseup', ['$event'])
  onMouseUp(evt: MouseEvent) {
    this.mousedown = false
  }

  onMouseDown(evt: MouseEvent) {
    this.mousedown = true
    this.selectedHeight = evt.offsetY
    this.draw()
    this.emitColor(evt.offsetX, evt.offsetY)
  }

  onMouseMove(evt: MouseEvent) {
    if (this.mousedown) {
      this.selectedHeight = evt.offsetY
      this.draw()
      this.emitColor(evt.offsetX, evt.offsetY)
    }
  }

  emitColor(x: number, y: number) {
    const rgbaColor = this.getColorAtPosition(x, y)
    this.color.emit(rgbaColor)
  }

  getColorAtPosition(x: number, y: number) {
    // @ts-ignore
    const imageData = this.ctx.getImageData(x, y, 1, 1).data
    return (
      'rgba(' + imageData[0] + ',' + imageData[1] + ',' + imageData[2] + ',1)'
    )
  }
}
