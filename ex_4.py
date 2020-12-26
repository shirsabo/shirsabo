# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

import random
import sys
import numpy as np
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import transforms as tr
from torch.utils.data.sampler import SubsetRandomSampler
from torchvision import datasets
IMAGE_SIZE = 28 * 28
LEARN_RATE = 0.001
EPOCHS = 10
HIDDEN_LAYER_SIZE_1 = 100
HIDDEN_LAYER_SIZE_2 = 50
LAYER_SIZE_OUT = 10


class ModelA(nn.Module):
    def __init__(self, image_size):
        super(ModelA, self).__init__()
        self.image_size = image_size
        # first layer
        self.fc0 = torch.nn.Linear(image_size, HIDDEN_LAYER_SIZE_1)
        # second layer
        self.fc1 = nn.Linear(HIDDEN_LAYER_SIZE_1, HIDDEN_LAYER_SIZE_2)
        # third layer
        self.fc2 = nn.Linear(HIDDEN_LAYER_SIZE_2, LAYER_SIZE_OUT)

    def forward(self, x):
        x = x.view(-1, self.image_size)
        x = F.relu(self.fc0(x))
        x = F.relu(self.fc1(x))
        x = self.fc2(x)
        return F.log_softmax(x, dim=1)

class ModelC(nn.Module):
    def __init__(self, image_size):
        super(ModelA, self).__init__()
        self.image_size = image_size
        # first layer
        self.fc0 = nn.Linear(image_size, HIDDEN_LAYER_SIZE_1)
        # second layer
        self.fc1 = nn.Linear(HIDDEN_LAYER_SIZE_1, HIDDEN_LAYER_SIZE_2)
        # third layer
        self.fc2 = nn.Linear(HIDDEN_LAYER_SIZE_2, LAYER_SIZE_OUT)

    def forward(self, x):
        x = x.view(-1, self.image_size)
        x = F.relu(self.fc0(x))
        # ACTIVATE DROPOUT after relu
        x = F.dropout(x, training=self.training)
        x = F.relu(self.fc1(x))
        # ACTIVATE DROPOUT after relu
        x = F.dropout(x, training=self.training)
        x = self.fc2(x)
        return F.log_softmax(x, dim=1)

class ModelE(nn.Module):
    def __init__(self, image_size):
        super(ModelE, self).__init__()
        self.image_size = image_size
        self.fc0 = nn.Linear(image_size, 128)
        self.fc1 = nn.Linear(128, 64)
        self.fc2 = nn.Linear(64, 10)
        self.fc3 = nn.Linear(10, 10)
        self.fc4 = nn.Linear(10, 10)
        self.fc5 = nn.Linear(10, LAYER_SIZE_OUT)

    def forward(self, x):
        x = x.view(-1, self.image_size)
        x = F.relu(self.fc0(x))
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = F.relu(self.fc3(x))
        x = F.relu(self.fc4(x))
        x = self.fc5(x)
        return F.log_softmax(x, dim=1)


def mini_set():
    # parameters should be: train_x train_y
    training_examples, training_labels = sys.argv[1], sys.argv[2]
    taken = random.sample(range(1, 55001), 5000)
    taken.sort()
    with open('train_x_short', 'w') as out1:
        i = 1
        f1 = open(training_examples)
        for line1 in f1:
            if i in taken:
                out1.write(line1)
            i += 1
        f1.close()
    with open('train_y_short', 'w') as out2:
        i = 1
        f2 = open(training_labels)
        for line2 in f2:
            if i in taken:
                out2.write(line2)
            i += 1
        f2.close()
#(model=model, train_loader=train_loader, optimizer=optimizer, validation_loader=validation_loader)
def train(model, train_loader, optimizer,validation_loader,batch_size):
    avg_train_loss = {}
    avg_validation_loss = {}
    avg_acc_train = {}
    avg_acc_validation = {}
    transform = tr.Compose([tr.ToTensor()])
    for epoch in range(EPOCHS):
        model.train()
        correct = 0
        T_loss = 0
        for batch_idx, (data, labels) in enumerate(train_loader):
            optimizer.zero_grad()
            output = model(data)
            loss = F.nll_loss(output, labels)
            loss.backward()
            optimizer.step()
            # argmax of output
            pred = output.data.max(1, keepdim=True)[1]
            T_loss += loss
            optimizer.step()
            correct += pred.eq(labels.data.view_as(pred)).cpu().sum()

        avg_train_loss[epoch], avg_acc_train[epoch] = calculate_loss_accurancy(T_loss,len(train_loader),batch_size,correct)
        avg_validation_loss[epoch], avg_acc_validation[epoch] = validate(model, validation_loader)
    #train_validation_graphs(avg_train_loss, avg_validation_loss)
    #acc_graphs(avg_acc_train, avg_acc_validation)
    test(model,transform)


def test(model,transform):
    test_x = np.loadtxt(sys.argv[3])
    test_x /= 256.0
    test_x = transform(test_x)[0].float()
    file = open("test_y", 'w+')
    for i in test_x:
        out = model(i)
        pred = out.max(1, keepdim=True)[1].item()
        mwss = str(pred) + '\n'
        file.write(mwss)
    file.close()

def calculate_loss_accurancy(T_loss,len_train_loader,batch_size,correct):
    T_loss /= (len(train_loader))
    acc = ((100. * correct.item()) / (len(train_loader) * batch_size))
    return T_loss, acc


def validate(model, test_loader):
    model.eval()
    test_loss = 0
    correct = 0
    for data, target in test_loader:
        output = model(data)
        test_loss += F.nll_loss(output, target, size_average=False)
        pred = output.data.max(1, keepdim=True)[1]
        correct += pred.eq(target.data.view_as(pred)).cpu().sum()
    return calculate_loss_accurancy(test_loss, len(test_loader), 1, correct)

def load_data(batch_size):
    # normalization : divide with min + max where min = 1, max = 255
    train_x = datasets.FashionMNIST(root='./data', train=True, transform=tr.ToTensor(), download=True)
    full_data_set_size = len(train_x)
    indices_train = list(range( full_data_set_size))
    validation_size = int(full_data_set_size * 0.2)
    validation_indices = np.random.choice(indices_train, size=validation_size, replace=False)
    train_indexes = list(set(indices_train) - set(validation_indices))
    train_samp = SubsetRandomSampler(train_indexes)
    validation_samp = SubsetRandomSampler(validation_indices)
    train_loader1 = torch.utils.data.DataLoader(dataset = train_x, batch_size=batch_size, sampler=train_samp)
    validation_loader1 = torch.utils.data.DataLoader(dataset = train_x, batch_size=1, sampler=validation_samp)
    return train_loader1, validation_loader1


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
   # mini_set()
    # load train_x,train_y and test_x
    train_loader, validation_loader = load_data(64)
    model = ModelA(image_size=IMAGE_SIZE)
    optimizer = optim.SGD(model.parameters(), lr=LEARN_RATE)
    train(model,train_loader,optimizer,validation_loader,64)
