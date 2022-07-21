import {RetroCard} from "./retro-card";

export interface RetroLane {
  id:number;
  name:String;
  color:String;
  cards:RetroCard[];
}
