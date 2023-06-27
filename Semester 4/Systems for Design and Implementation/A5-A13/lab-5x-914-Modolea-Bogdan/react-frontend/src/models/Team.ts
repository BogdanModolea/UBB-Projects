import { League } from "./League";

export interface Team {
    tid: number;
    name: string;
    top: string;
    jungle: string;
    mid: string;
    bot: string;
    support: string;
    league: League;
}