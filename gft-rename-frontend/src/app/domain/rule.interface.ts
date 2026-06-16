export interface Rule {
  id: number
  name: string
  order: number
  toCheck: string
  toReplace: string
  type: "PREFIX" | "SUFFIX"
}