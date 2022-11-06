import i18n, { isEN } from "@/i18n";
import { TreeNodeType } from '@/utils/constants'
import { ITreeNode } from '@/types'

export function formatDate(date:any, fmt = 'yyyy-MM-dd') {
  if (!date) {
    return '';
  }
  if (typeof date == 'number' || typeof date == 'string') {
    date = new Date(date);
  }
  if (!(date instanceof Date) || isNaN(date.getTime())) {
    return '';
  }
  var o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds(),
    'q+': Math.floor((date.getMonth() + 3) / 3),
    S: date.getMilliseconds(),
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
  for (var k in o)
    if (new RegExp('(' + k + ')').test(fmt))
      fmt = fmt.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
  return fmt;
}

const monthNamesEn = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

export function formatNaturalDate(date: any) {
  if (!date) {
    return '';
  }
  if (typeof date == 'number' || typeof date == 'string') {
    date = new Date(date);
  }
  const d = date as Date;
  const D = new Date();
  const i = +d;
  const diff = i - +D;
  const minutes = Math.abs(diff / 60000);
  if (minutes < 1) {
    return diff > 0 ? i18n('common.tip.now') : i18n('common.tip.justNow');
  }
  if (minutes < 60) {
    return `${i18n('common.data.minute', ~~minutes)}${diff > 0 ? i18n('common.tip.later') : i18n('common.tip.ago')}`;
  }
  const relativeIndex = [-1, 0, 1].findIndex(t => {
    const D = new Date();
    const date = D.getDate() + t;
    D.setDate(date);
    D.setHours(0, 0, 0, 0);
    const start = +D;
    D.setDate(date + 1);
    const end = +D;
    return i > start && i <= end;
  });
  if (relativeIndex > -1) {
    const t = formatDate(d, 'hh:mm')
    return [
      i18n('common.tip.yesterday', t),
      t,
      i18n('common.tip.tomorrow', t),
    ][relativeIndex]
  }
  if (d.getFullYear() === D.getFullYear()) {
    return isEN() ? `${d.getDate()} ${monthNamesEn[d.getMonth()]}` : formatDate(d, 'M月d日');
  }
  return formatDate(d);
}

export function toTreeList(data:any[],name:string,type:string,nodeType:TreeNodeType,isLeaf=true){
  return data?.map((item,index)=>{
    return {
      key: `${index+1}-${index+1}`,
      dataType: item[type],
      nodeType: nodeType,
      name: item[name],
      isLeaf,
    }
  })
}

// 生成一个随机数
export function createRandom(minNum:number,maxNum:number){
  return Math.floor(Math.random()*(maxNum-minNum+1)+minNum); 
}

// 
export function createRandomId(length:number){
}

// 模糊匹配树并且高亮
export function approximateTreeNode(treeData: ITreeNode[], target: string, isDelete = true){
  if(target){
    const newTree:ITreeNode[] = JSON.parse(JSON.stringify(treeData));
    newTree.map((item,index)=>{
      if(item.children?.length){
        item.children = approximateTreeNode(item.children, target,false);
      }
      if(item.name?.toUpperCase()?.indexOf(target?.toUpperCase()) == -1 && isDelete){
        delete newTree[index];
      }else{
        item.name = item.name?.replace(target,`<span style='color:red;'>${target}</span>`);
      }
    })
    return newTree.filter(i=>i)
  }else{
    return treeData
  }
}

export function getLocationHash(){
    const righthash = location.hash.split('?')[1]
    const params:any = {}
    if (righthash) {
      const arr = righthash.split('&')
      arr.map(item => {
        const splitRes = item.split('=')
        params[splitRes[0]] = splitRes[1]
      })
    }
    return params
}