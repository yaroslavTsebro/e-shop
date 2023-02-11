function addPhotoFolder(){
    let root = document.getElementById("photos")
    var aTag = document.createElement("br");
    var file = document.createElement("input");
    file.name = 'file';
    file.type = 'file';
    root.appendChild(aTag);
    root.appendChild(file);
}

function deletePhotoFolder(){
    let root = document.getElementById("photos");
    root.removeChild(root.lastChild);
    root.removeChild(root.lastChild);
}

function addCharacteristic(){
    let node = document.getElementById("characteristic");
    let newElem = node.cloneNode(true);
    document.getElementById("characteristics").appendChild(newElem);
}
function deleteCharacteristic(){
    let root = document.getElementById("characteristics");
    root.removeChild(root.lastChild);
    root.removeChild(root.lastChild);
}
