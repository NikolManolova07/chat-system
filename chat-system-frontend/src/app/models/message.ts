import { DatePipe } from "@angular/common";
import { Channel } from "./channel.model";
import { User } from "./user.model";

export interface Message {
    sender: User;
    receiver: User | null;
    channel: Channel | null;
    content: string;
    createdAt: DatePipe;
}