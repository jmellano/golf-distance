import { BaseEntity } from './../../shared';

export class Player implements BaseEntity {
    constructor(
        public id?: number,
        public lastName?: string,
        public firstName?: string,
        public userId?: number,
        public hasClubs?: BaseEntity[],
    ) {
    }
}
