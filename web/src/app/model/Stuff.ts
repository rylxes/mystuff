import {Category} from "./Category";

export class Stuff {

  constructor(
    public id: number,
    public name: string,
    public description: string,
    public categories: Category[]
  ) {}
}
