const arrOne = [5, 19, 8, 1];
const arrTwo = [10, 10];
const arrThree = [3, 0, 5];
const solution = (arr) => {
    let arrSum = 0;
    arr.forEach(e => arrSum += e);
    let currentSum = arrSum;
    let arrayCopy = arr.slice();
    let result = 0;
    while (currentSum > arrSum / 2){
        let max = Math.max(...arrayCopy);
        let index = arrayCopy.indexOf(max);
        arrayCopy[index] = max / 2;
        result += 1;
        currentSum = 0;
        arrayCopy.forEach(e => currentSum += e);
    }
    return result;
}
solution(arrOne);
solution(arrTwo);
solution(arrThree);

