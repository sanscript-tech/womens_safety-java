from torchvision import models
import torch
dir(models)

alexnet = models.alexnet(pretrained=True)

from torchvision import transforms

#changing the dimensions of the image.
transform = transforms.Compose([
 transforms.Resize(256),                  
 transforms.CenterCrop(224),                
 transforms.ToTensor(),                    
 transforms.Normalize(                      
 mean=[0.485, 0.456, 0.406],                
 std=[0.229, 0.224, 0.225]                  
 )])

#dummy path for the image
img = '/Users/khushboogupta/Desktop/dos.jpg'
img_t = transform(img)
batch_t = torch.unsqueeze(img_t, 0)

#evaluation
alexnet.eval()

out = alexnet(batch_t)
with open('imagenet_classes.txt') as f:
  classes = [line.strip() for line in f.readlines()]
_, index = torch.max(out, 1)

percentage = torch.nn.functional.softmax(out, dim=1)[0] * 100
#printing the label along with the accuracy.
print(labels[index[0]], percentage[index[0]].item())

