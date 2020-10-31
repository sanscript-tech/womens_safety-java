import uvicorn
from fastapi import FastAPI, File, UploadFile
from cv2 import cv2
from PIL import Image

app = FastAPI()

def predict(file: Image.Image):
    image = cv2.imread(file)
    return image.size

@app.get("/")
def read_root():
    return {"Bhavti Abhay"}

@app.post("/predict/")
async def predict_api(file: UploadFile = File(...)):
    extension = file.filename.split(".")[-1] in ("jpg", "jpeg", "png")
    if not extension:
        return "Image must be jpg or png format!"  
    prediction = predict(file)
    return prediction

if __name__ == "__main__":
    uvicorn.run(app, debug=True)
