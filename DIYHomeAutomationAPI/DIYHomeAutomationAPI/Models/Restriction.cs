using DIYHomeAutomationAPI.Models.Enums;

namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a Restriction
    /// </summary>
    public class Restriction
    {

        /// <summary>
        /// Restriction's Id
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Restriction's Name
        /// </summary>
        public string Name { get; set; } = null!;

        /// <summary>
        /// Restriction's State
        /// </summary>
        public bool State { get; set; }

        /// <summary>
        /// Restriction's Condition
        /// </summary>
        public TypeCondition Condition { get; set; }

        /// <summary>
        /// Primary Device's Id
        /// </summary>
        public int PrimaryDeviceId { get; set; }

        /// <summary>
        /// Primary Device's State
        /// </summary>
        public bool PrimaryDeviceState { get; set; }

        /// <summary>
        /// Primary Device's Value
        /// </summary>
        public double PrimaryDeviceValue { get; set; }

        /// <summary>
        /// Secondary Device's Id
        /// </summary>
        public int? SecondaryDeviceId { get; set; }

        /// <summary>
        /// Secondary Device's State
        /// </summary>
        public bool? SecondaryDeviceState { get; set; }

        /// <summary>
        /// Secondary Device's Value
        /// </summary>
        public double? SecondaryDeviceValue { get; set; }
    }
}
