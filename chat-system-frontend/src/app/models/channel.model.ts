import { DatePipe } from "@angular/common";
import { User } from "./user.model";

export interface Channel {
    id: number;
    name: string;
    owner: User;
    createdAt?: DatePipe;
    updatedAt?: DatePipe;
}