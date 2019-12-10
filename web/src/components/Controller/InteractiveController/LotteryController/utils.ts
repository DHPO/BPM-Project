export function randInt(start: number, end: number) {
  start = Math.floor(start);
  end = Math.floor(end);
  return Math.floor(Math.random() * (end - start)) + start;
}

export function randChooseSome(items: any[], num: number) {
  if (items.length < num) {
    throw 1;
  }

  const pool = [...items];

  const choose: number[] = [];
  for (let _ = 0; _ < num; _++) {
    const rand = randInt(0, pool.length);
    choose.push(pool[rand]);
    pool.splice(rand, 1);
  }

  return choose;
}
