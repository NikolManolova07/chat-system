import { DatePipe } from "@angular/common";
import { User } from "./user.model";

export interface Member {
    user: User;
    role: string;
    joinedAt: DatePipe;
}