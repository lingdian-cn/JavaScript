import cloneDeep from './cloneDeep'

let obj = {
    a: 1,
    b: 'b',
    c: [1, 2, 3],
    d: {
        d1: 1,
        d2: 'haha'
    }
}

console.log('cloneDeep', cloneDeep(obj))
