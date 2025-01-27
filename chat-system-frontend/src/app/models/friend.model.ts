import { DatePipe } from "@angular/common";
import { User } from "./user.model";

export interface Friend {
    user: User;
    friend: User;
    createdAt: DatePipe;
}