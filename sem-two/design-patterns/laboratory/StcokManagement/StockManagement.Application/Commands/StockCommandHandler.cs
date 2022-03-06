using MediatR;
using StockManagement.Application.Enums;
using StockManagement.Application.Interfaces;
using StockManagement.Domain.Entities;
using StockManagement.Domain.Events;

namespace StockManagement.Application.Commands;

public class StockCommandHandler :
    IRequestHandler<AddStockCommand, AddStockCommandResponse>,
    IRequestHandler<EditStockCommand, EditStockCommandResponse>,
    IRequestHandler<DeleteStockCommand, Unit>
{

    private readonly IRepository _repository;
    private readonly IEventDispatcher _eventDispatcher;

    public StockCommandHandler(IRepository repository, IEventDispatcher eventDispatcher)
    {
        _repository = repository;
        _eventDispatcher = eventDispatcher;
    }

    public async Task<AddStockCommandResponse> Handle(AddStockCommand request, CancellationToken cancellationToken)
    {
        var stockId = Guid.NewGuid();
        using var unitOfWork = _repository.CreateUnitOfWork();
        var stock = new Stock
        {
            Id = stockId,
            BestBeforeDate = request.Stock.BestBeforeDate,
            Item = request.Stock.Item,
            Location = request.Stock.Location,
            LicensePlate = request.Stock.LicensePlate,
            Lot = request.Stock.Lot,
            StockStatus = request.Stock.StockStatus
        };
        unitOfWork.Add(stock);
        await unitOfWork.SaveChangesAsync(cancellationToken);
        
        _eventDispatcher.Publish(new StockAdded(stockId));
        
        return new AddStockCommandResponse
        {
            Id = stockId
        };
    }

    public async Task<EditStockCommandResponse> Handle(EditStockCommand request, CancellationToken cancellationToken)
    {
        using var unitOfWork = _repository.CreateUnitOfWork();
        var currentStock = unitOfWork.GetEntities<Stock>()
            .FirstOrDefault(e => e.Id == request.Stock.Id);
        if (currentStock == null)
        {
            throw new Exception();
        }

        currentStock.BestBeforeDate = request.Stock.BestBeforeDate;
        currentStock.Item = request.Stock.Item;
        currentStock.Location = request.Stock.Location;
        currentStock.LicensePlate = request.Stock.LicensePlate;
        currentStock.Lot = request.Stock.Lot;

        await unitOfWork.SaveChangesAsync(cancellationToken);
        return new EditStockCommandResponse
        {
            Id = request.Stock.Id
        };
    }

    public async Task<Unit> Handle(DeleteStockCommand request, CancellationToken cancellationToken)
    {
        using var unitOfWork = _repository.CreateUnitOfWork();
        var currentStock = unitOfWork.GetEntities<Stock>()
            .FirstOrDefault(e => e.Id == request.Id);
        if (currentStock == null)
        {
            throw new Exception();
        }

        currentStock.StockStatus = StockStatus.Closed;

        await unitOfWork.SaveChangesAsync(cancellationToken);
        return Unit.Value;
    }
}